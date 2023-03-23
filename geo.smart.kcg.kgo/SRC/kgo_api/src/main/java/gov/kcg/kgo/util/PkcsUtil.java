package gov.kcg.kgo.util;

public class PkcsUtil {

	/**
	 * p12 to Pkcs7
	 *
	 * @param storeName
	 * @param storePass
	 * @param signData
	 * @return
	 * @throws KgoApiException
	 */
//    public byte[] p12ToPkcs7(String storeName,String storePass,String signData) throws KgoApiException {
//        try {
//            //First load the keystore object by providing the p12 file path
//            KeyStore clientStore = KeyStore.getInstance("PKCS12");
//            //replace testPass with the p12 password/pin
//            clientStore.load(new FileInputStream(storeName), storePass.toCharArray());
//
//            Enumeration<String> aliases = clientStore.aliases();
//            String aliaz = "";
//            while(aliases.hasMoreElements()){
//                aliaz = aliases.nextElement();
//                if(clientStore.isKeyEntry(aliaz)){
//                    break;
//                }
//            }
//            X509Certificate c = (X509Certificate)clientStore.getCertificate(aliaz);
//
//            //Data to sign
//            byte[] dataToSign = signData.getBytes();
//            //compute signature:
//            Signature signature = Signature.getInstance("Sha1WithRSA");
//            signature.initSign((PrivateKey)clientStore.getKey(aliaz, storePass.toCharArray()));
//            signature.update(dataToSign);
//            byte[] signedData = signature.sign();
//
//            //load X500Name
//            X500Name xName      = X500Name.asX500Name(c.getSubjectX500Principal());
//            //load serial number
//            BigInteger serial   = c.getSerialNumber();
//            //laod digest algorithm
//            AlgorithmId digestAlgorithmId = new AlgorithmId(AlgorithmId.SHA_oid);
//            //load signing algorithm
//            AlgorithmId signAlgorithmId = new AlgorithmId(AlgorithmId.RSAEncryption_oid);
//
//            //Create SignerInfo:
//            SignerInfo sInfo = new SignerInfo(xName, serial, digestAlgorithmId, signAlgorithmId, signedData);
//            //Create ContentInfo:
//            ContentInfo cInfo = new ContentInfo(ContentInfo.DIGESTED_DATA_OID, new DerValue(DerValue.tag_OctetString, dataToSign));
//            //Create PKCS7 Signed data
//            PKCS7 p7 = new PKCS7(new AlgorithmId[] { digestAlgorithmId }, cInfo,
//                    new java.security.cert.X509Certificate[] { c },
//                    new SignerInfo[] { sInfo });
//            //Write PKCS7 to bYteArray
//            ByteArrayOutputStream bOut = new DerOutputStream();
//            p7.encodeSignedData(bOut);
//            byte[] encodedPKCS7 = bOut.toByteArray();
//            return encodedPKCS7;
//        } catch (Exception e){
//            throw new KgoApiException(e);
//        }
//    }

}
