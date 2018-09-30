1) Generate the CSR

openssl req -new -newkey rsa:2048 -nodes -out www.coinstream.co.uk.csr -keyout www.coinstream.co.uk.key -subj "/C=GB/ST=England/L=London/O=Coinstream/OU=Technology/CN=coinstream.co.uk"

2) Upload CSR

Take the .csr file and post on to GoDaddy.com SSL certificate setup page

3) Download the .pem and .crt files

Rename them godaddy.crt, godaddy.crt.pem, and www.coinstream.co.uk.crt

4) Generate the PKCS#12 file

openssl pkcs12 -export -out www.coinstream.co.uk.pfx -inkey www.coinstream.co.uk.key -in www.coinstream.co.uk.crt -certfile godaddy.crt

