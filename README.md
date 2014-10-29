MySWORD
======
This is a project for making a plugin for SWORD.

##<a name="menu"/>Menu
------
* [Agreement Tools](#agreement)
  * [Run Agreement](#runAgreement) 
  * [Reload Agreement](#reloadAgreement)
* [Masstest Tools](#masstest)
* [Map Tools](#map)

##<a name="agreement"/>Agreement Tools
     Agreement tools are mainly focus on using web UI to replace running unix commands manually.
####<a name="runAgreement"/>Run Agreement
     Using jsch connect to UNIX server and run the commands.
     1. Receive the agreementID and instance from web client.
     2. Connect to application server.
     3. Run command: run <AgreementID>.
     4. Input the user's name.
     5. Input the comsys instance.
     6. Done.
####<a name="reloadAgreement"/>Reload Agreement
     Using jsch connect to UNIX server and run the commands.
##<a name="masstest"/>Masstest Tools
     Using jsch SFTP/SSH2 to masstest FTP server.
####<a name="uploadFile"/>Upload Files
     Using jsch SFTP to masstest SFTP server, then upload files.
######<a name="uploadLocalFile"/>Upload Local File
     Using AJAX to send the files to beckend system, then upload the files to SFTP server.
######<a name="uploadSpecificData"/>Upload Specific Data
     Using AJAX to send the data with specific encoding to beckend system, then upload the files to SFTP server.
##<a name="map"/>Map Tools
