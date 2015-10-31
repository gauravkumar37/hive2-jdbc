# Hive JDBC Connection Examples

This project showcases how to connect to Hiveserver2 using a variety of different methods.
All the classes work only with Hiveserver2.
Cloudera JDBC drivers are being used which can be downloaded from [here](http://www.cloudera.com/content/www/en-us/downloads/connectors/hive/jdbc/2-5-15.html).
At the time of writing this, latest version is `v2.5.15`.

## Requisites:
You need to download the drivers and copy them to the `lib` folder.

1. Download the zip from from [here](http://www.cloudera.com/content/www/en-us/downloads/connectors/hive/jdbc/2-5-15.html) for the correct OS and architecture.
2. Unzip and go to `Cloudera_HiveJDBC41_xxx` folder.
3. Copy all the jars from there to the `lib` folder.

JDBC API v3.x supports JRE v4.0 or v5.0

JDBC API v4.x supports JRE v6.0 or later

JDBC API v4.1.x supports JRE v7.0 or later

The Cloudera JDBC Driver for Apache Hive supports Hive 0.11, 0.12, 0.13, 0.14, 1.0, and 1.1.

Refer to the PDF documentation in the downloaded zip for more information.


## Notes:
1. Do change the JDBC URL given at the starting of each class according to your cluster.
2. If the table `sample_07` does not exist in `default` database, change the query accordingly.
3. `pom.xml` contains Java version as 1.8. If you don't have 1.8, change the version accordingly.


## Authentication Types

There are 4 examples in this project:

1. Hive2User

This class works with `hive.server2.authentication` set to `None`.
You need to specify the username only.

2. Hive2UserPswd

This class works  with `hive.server2.authentication` set to `LDAP` or `None`.
You need to specify both the username and password.

3. Hive2KerberosKinit

This class works  with `hive.server2.authentication` set to `Kerberos`.
The onus of generating a valid Kerberos ticket lies with the client.
This assumes that:
	1. Kerberos client utilities are installed in the system.
	2. Valid krb5.ini file is present in `/etc/krb5.ini`
	3. Client has obtained a valid ticket beforehand by calling `kinit` or authenticating with a valid `keytab` file.

4. Hive2KerberosTGT
This class also works  with `hive.server2.authentication` set to `Kerberos`.
However, in this case, the code/driver obtains the Kerberos ticket.
The code needs 3 files (currently present in `conf` directory):
	1. `kerberos_login_config.ini`
	2. `krb5.ini`
	3. `<username>.keytab`

	Requisites:

	1. Obtain a valid `krb5.ini` file from your Kerberos administrator and place it in the `conf` directory.
	2. Obtain a valid `keytab` file for your principal from your Kerberos administrator and place it in `conf` directory with the name as `<username>.keytab`.  
	3. Modify `kerberos_login_config.ini` and change these 2 settings:
		1. `keyTab`: should point to the correct keytab file. Relative paths work. Replace `<username>` with the correct one.
		2. `principal`: insert the correct principal. This consists of 2 parts: username and realm.
		Replace `<username>` with the correct one as above.
		Replace `<REALM>` with the correct one. This should all be in caps. You can find the correct realm in the `krb5.ini` file provided to you by the admin.
	4. Make sure you can connect to the hosts pointed to in the `krb5.ini` file. If neccessary, add entries in the `hosts` file. 
