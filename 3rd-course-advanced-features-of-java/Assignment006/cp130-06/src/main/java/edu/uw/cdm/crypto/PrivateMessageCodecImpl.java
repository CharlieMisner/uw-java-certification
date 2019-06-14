package edu.uw.cdm.crypto;

import edu.uw.ext.framework.crypto.PrivateMessageCodec;
import edu.uw.ext.framework.crypto.PrivateMessageTriple;
import javax.crypto.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class PrivateMessageCodecImpl implements PrivateMessageCodec {

    private static final String ENCRYPTION_ALGORITHM = "RSA";

    public PrivateMessageCodecImpl() {
    }

    @Override
    public PrivateMessageTriple encipher(
        byte[] plaintext,
        String senderKeyStoreName,
        char[] senderKeyStorePasswd,
        String senderKeyName,
        char[] senderKeyPasswd,
        String senderTrustStoreName,
        char[] senderTrustStorePasswd,
        String recipientCertName)
        throws GeneralSecurityException, IOException {

        // Step 1) Generate a one-time use shared symmetric secret key.
        SecretKey secretKey = this.generateSecretKey();

        // Step 2) Encipher the the order data using the one-time use shared symmetric secret key.
        byte[] encryptedData = this.encipherData(secretKey, plaintext, secretKey.getAlgorithm());

        // Step 3) Obtain the bytes representing the one-time use shared symmetric secret key.
        byte[] keyBytes = secretKey.getEncoded();

        // Step 4) Retrieve the (broker's) public key from the provided truststore.
        PublicKey brokerPublicKey = (PublicKey)this.getKey(senderTrustStorePasswd, senderTrustStoreName, new char[0]);

        // Step 5) Encipher the shared symmetric secret key's bytes using the public key from the truststore.
        byte[] encryptedSecretKey = this.encipherData(brokerPublicKey, keyBytes, ENCRYPTION_ALGORITHM);

        // Step 6) Retrieve the (client's) private key from the the provided keystore.
        PrivateKey clientPrivateKey = (PrivateKey)this.getKey(senderKeyStorePasswd, senderKeyStoreName, senderKeyPasswd);

        // Step 7) Sign the plaintext order data using the private key from the the provided keystore.
        Signature signer = Signature.getInstance("SHA256withRSA");
        signer.initSign(clientPrivateKey);
        signer.update(encryptedData);

        // Step 8) Construct and return a PrivateMessageTriple containing the ciphertext, key bytes and signature.
        PrivateMessageTriple privateMessageTriple = new PrivateMessageTriple(encryptedSecretKey,encryptedData, signer.sign());
        return privateMessageTriple;
    }

    private SecretKey generateSecretKey(){
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        generator.init(128);
        return generator.generateKey();
    }

    private byte[] encipherData(Key key, byte[] data, String algorithm){
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedData = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e){
            e.printStackTrace();
        } catch (IllegalBlockSizeException e){
            e.printStackTrace();
        } catch (BadPaddingException e){
            e.printStackTrace();
        }

        return encryptedData;
    }

    private Key getKey(char[] storePass, String name, char[] keyPass){
        Key key;
        try {
            FileInputStream inputStream = new FileInputStream(String.format("src/main/resources/%s", name));
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(inputStream, storePass);
            String alias = this.getAliasFromName(name);
            if(keyStore.isCertificateEntry(alias)){
                Certificate cert = keyStore.getCertificate(alias);
                key = cert.getPublicKey();
            } else {
                key = keyStore.getKey(alias, keyPass);
            }
        } catch (KeyStoreException e) {
            key = null;
            e.printStackTrace();
        } catch (IOException e) {
            key = null;
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            key = null;
            e.printStackTrace();
        } catch (CertificateException e) {
            key = null;
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            key = null;
            e.printStackTrace();
        }

        return key;
    }

    @Override
    public byte[] decipher(
        PrivateMessageTriple privateMessageTriple,
        String s,
        char[] chars,
        String s1,
        char[] chars1,
        String s2,
        char[] chars2,
        String s3)
        throws GeneralSecurityException, IOException {

        return new byte[0];
    }


    private String getAliasFromName(String name){
        String alias = "";
        switch (name){
            case "brokerCert.crt":
                alias = "brokerPrivKey";
                break;
            case "brokerKey.jck":
                alias = "brokerprivkey";
                break;
            case "brokerTrust.jck":
                alias = "clientprivkey";
                break;
            case "clientCert.crt":
                alias = "clientPrivKey";
                break;
            case "clientKey.jck":
                alias = "clientprivkey";
                break;
            case "clientTrust.jck":
                alias = "brokerprivkey";
                break;
        }
        return alias;
    }
}
