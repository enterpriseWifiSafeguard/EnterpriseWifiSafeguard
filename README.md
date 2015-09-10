# EnterpriseWifiSafeguard

## Introduction
EnterpriseWifiSafeguard is an Android App for setting up Wifis with WPA/WPA2-Enterprise. It provides a lot of built in certificates and it is possible to set the
subject_match option from the WPA_Supplicant for security reasons. 

*Note*: the app is still under development

## Setup Instructions

The settings heavily depend on what your Wifi provider requires. For the University of Konstanz the following screenshot will be helpfull:

![Sample Config University of Konstanz](config-screen.png)

## Import / Export
The App has the option to import a config - file or to export it. You can reach these options by opening the menu (three dots at the top). If you export your configuration
the first time a folder "EnterpriseWifiSafeguard" will be created on your internal SD-card and you can find the config.txt in there. You can share this folder with 
your friends and they can simply import the configuration from by copy the folder on their internal SD-Card and pressing the import button.

Note:
* The config-file will be overitten if you push the export button. If you want it as backup move it on another place.
* Because of technical reasons of the android API it is not possible to choose the config-file or the path
* There are _no_ personal informations stored in the file (like username or password)
* The order of the options has to be: ssid, anonymous identity, servername, eap method, phase2 method, certificate
