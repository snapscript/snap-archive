1) Generate the CSR

openssl req -new -newkey rsa:2048 -nodes -out www.orthrus.io.csr -keyout www.orthrus.io.key -subj "/C=GB/ST=England/L=London/O=Orthrus/OU=Technology/CN=orthrus.io"

2) Upload CSR

Take the .csr file and post on to GoDaddy.com SSL certificate setup page

3) Download the .pem and .crt files

Rename them godaddy.crt, godaddy.crt.pem, and www.orthrus.io.crt

4) Generate the PKCS#12 file

openssl pkcs12 -export -out www.orthrus.io.pfx -inkey www.orthrus.io.key -in www.orthrus.io.crt -certfile godaddy.crt

