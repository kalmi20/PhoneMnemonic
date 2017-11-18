#! /bin/sh

SOURCE_DIRECTORY=$1
shift

java -jar ./target/PhoneMnemonic-jar-with-dependencies.jar -t service -i $SOURCE_DIRECTORY -p "$*"

read -p "Press enter to continue"
