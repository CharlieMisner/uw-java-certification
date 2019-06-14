package edu.uw.cdm.crypto;

import edu.uw.ext.framework.crypto.PrivateMessageCodec;
import edu.uw.ext.framework.crypto.PrivateMessageTriple;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;

public class PrivateMessageCodecImpl implements PrivateMessageCodec {

    private static final String ENCRYPTION_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

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
        Signature signer = Signature.getInstance(SIGNATURE_ALGORITHM);
        signer.initSign(clientPrivateKey);
        signer.update(encryptedData);

        // Step 8) Construct and return a PrivateMessageTriple containing the ciphertext, key bytes and signature.
        PrivateMessageTriple privateMessageTriple = new PrivateMessageTriple(encryptedSecretKey,encryptedData, signer.sign());
        return privateMessageTriple;
    }

    @Override
    public byte[] decipher(
            PrivateMessageTriple triple,
            String recipientKeyStoreName,
            char[] recipientKeyStorePasswd,
            String recipientKeyName,
            char[] recipientKeyPasswd,
            String trustStoreName,
            char[] trustStorePasswd,
            String signerCertName)
            throws GeneralSecurityException, IOException {

        // Step 1) Obtain the shared secret key, order data ciphertext and signature from the provided PrivateMessageTriple
        byte[] encryptedSecretKey = triple.getEncipheredSharedKey();
        byte[] encipheredData = triple.getCiphertext();
        byte[] signature = triple.getSignature();

        // Step 2) Retrieve the (brokers's) private key from the the provided keystore
        PrivateKey brokersPrivateKey = (PrivateKey)this.getKey(recipientKeyStorePasswd, recipientKeyStoreName, recipientKeyPasswd);

        // Step 3) Use the private key from the keystore to decipher the shared secret key's bytes
        byte[] decryptedBrokerPrivateKeyBytes = this.decipherData(brokersPrivateKey, encryptedSecretKey, ENCRYPTION_ALGORITHM);

        // Step 4) Reconstruct the shared secret key from shared secret key's bytes
        SecretKey secretKey = new SecretKeySpec(decryptedBrokerPrivateKeyBytes, 0, decryptedBrokerPrivateKeyBytes.length,"AES");

        // Step 5) Use the shared secret key to decipher the order data ciphertext
        byte[] plainText = this.decipherData(secretKey, encipheredData, secretKey.getAlgorithm());

        // Step 6) Retrieve the (client's) public key from the provided truststore
        PublicKey clientsPublicKey = (PublicKey)this.getKey(trustStorePasswd, trustStoreName, new char[0]);

        // Step 7)
        boolean isValidSignature = this.isValidSignature(encipheredData, clientsPublicKey, signature);
        if(isValidSignature){
            System.out.println("The signature is valid.");
        } else {
            System.out.println("The signature is invalid.");
        }

        // Step 8) Return the order data plaintext
        return plainText;
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

    private byte[] decipherData(Key key, byte[] data, String algorithm){
        byte[] encryptedData = null;
        try {
            Cipher decipher = Cipher.getInstance(algorithm);
            decipher.init(Cipher.DECRYPT_MODE, key);
            encryptedData = decipher.doFinal(data);
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

    private boolean isValidSignature(byte[] data, PublicKey key, byte[] signature) {
        boolean verify = false;
        try {
            Signature verifier = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifier.initVerify(key);
            verifier.update(data);
            verify =  verifier.verify(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return verify;
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
