package io.orthrus.common.iam;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class PasswordGenerator {

   // Used to generate the HMAC signature. Do not modify.
   private static final String MESSAGE = "SendRawEmail"; 
   // Version number. Do not modify.
   private static final byte VERSION =  0x02; 

   @SneakyThrows
   public static String generate(String key) {
      return generate(key, MESSAGE);
   }
   
   @SneakyThrows
   public static String generate(String key, String message) {   
      // Create an HMAC-SHA256 key from the raw bytes of the AWS secret access key.
      SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
         
      // Get an HMAC-SHA256 Mac instance and initialize it with the AWS secret access key.
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(secretKey);

      // Compute the HMAC signature on the input data bytes.
      byte[] rawSignature = mac.doFinal(message.getBytes());

      // Prepend the version number to the signature.
      byte[] rawSignatureWithVersion = new byte[rawSignature.length + 1];               
      byte[] versionArray = {VERSION};                
      System.arraycopy(versionArray, 0, rawSignatureWithVersion, 0, 1);
      System.arraycopy(rawSignature, 0, rawSignatureWithVersion, 1, rawSignature.length);

      // To get the final SMTP password, convert the HMAC signature to base 64.
      return DatatypeConverter.printBase64Binary(rawSignatureWithVersion);                 
   }
   
   public static void main(String[] list) {
      System.err.println(generate("ses-smtp-user.20181013-203955"));
   }
}