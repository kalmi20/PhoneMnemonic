#! /bin/sh

OUTPUT_DIRECTORY=$1
SOURCE_DIRECTORY=$2

java -Xmx8g -Xms3g -jar -Djava.util.concurrent.ForkJoinPool.common.parallelism=8 ./target/PhoneMnemonic-jar-with-dependencies.jar -t mine -s $SOURCE_DIRECTORY -i $OUTPUT_DIRECTORY

read -p "Press enter to continue"

