# Command to convert GoDaddy.com CA to PKCS12

openssl pkcs12 -export -out www.zuooh.com.p12 -inkey www.zuooh.com.key -in
www.zuooh.com.crt -certfile authority.pem

Password: password1


# Generate the CSR

openssl req -new -newkey rsa:2048 -nodes -out zuooh.com.csr -keyout zuooh.com.key -subj "/C=AU/ST=NSW/L=Sydney/O=Zuooh/OU=Technology/CN=zuooh.com"

Niall@Niall-PC ~/work/development/bitbucket/proxy/zuooh-standard-proxy-site/certificate
$ openssl req -new -newkey rsa:2048 -nodes -out zuooh.com.csr -keyout zuooh.com.key -subj "/C=AU/ST=NSW/L=Sydney/O=Zuooh/OU=Technology/CN=zuooh.com"
Generating a 2048 bit RSA private key
....+++
................................+++
writing new private key to 'zuooh.com.key'
-----

#Convert a Certificate File to PKCS#12 Format

If you obtained a certificate and its private key in PEM or another format, you must convert it to PKCS#12 (PFX) format before you can import the certificate into a Windows certificate store on a View server host.

You might obtain a certificate keystore file from a CA, or your organization might provide you with certificate files, in various formats. For example, your certificates might be in PEM format, which is often used in a Linux environment. Your files might have a certificate file, key file, and CSR file with the following extensions:

server.crt
server.csr
server.key

The CRT file contains the SSL certificate that was returned by the CA. The CSR file is the original certificate signing request file and is not needed. The KEY file contains the private key.
Prerequisites

Verify that OpenSSL is installed on the system. You can download openssl from http://www.openssl.org. To run openssl from any directory on the system, see Add openssl to the System Path.
Procedure


# Generate a PKCS#12 (PFX) keystore file from the certificate file and your private key.

For example: openssl pkcs12 -export -out server.p12 -inkey server.key -in server.crt -certfile CACert.crt

In this example, CACert.crt is the name of the root certificate that was returned by the certificate authority.
You can also generate a keystore with a PFX extension. For example: -out server.pfx
