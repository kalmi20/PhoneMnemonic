#! /bin/sh

OUTPUT_DIRECTORY=$1
SOURCE_DIRECTORY=$2

java -jar ./target/PhoneMnemonic.jar -t mine -s $SOURCE_DIRECTORY -i $OUTPUT_DIRECTORY

read -p "Press enter to continue"

