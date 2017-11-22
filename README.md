# PhoneMnemonic

## Installation
  Run build.sh to build the project, after that execute service.sh, data-mine.sh

## Miner
  Creates a token-tfidf index based on the source txt files located in the source directory.
  
  To run the miner please execute data-mine.sh with the output directory and source directory location.
  
  e.g: data-mine.sh C:\\indexDir C:\\source

  Performance:
  AMD FX-3850 8 core
  Samsung SSD 850 EVO
  8GB Ram
  Windows 10

  Used heap max: 5481MB
  Run time: 8 min


## Service
Takes a list of phone numbers as an input and looks up the tokens in the index file which could be represented by the input phone number.

To run the service please execute service.sh with the index directory location and the list of phone numbers as parameters.

e.g.: service.sh C:\\indexDir

## Bonuses
Bonus 1 : implemented

Bonus 2 : not implemented

Bonus 3 : Miner uses java8 forkJoinPools for processing the data, to set the parallelism level please specify
-Djava.util.concurrent.ForkJoinPool.common.parallelism=$PARALELLISM in the data-mine.sh, If not set default value is number of cpu cores minus 1.

Bonus 4 : Service reads all the .idx files inside the source directory and does the look up in all of them, to find the mnemonic.

.\service.sh C:\\out2
.\data-mine.sh C:\\out2 C:\\files2\\txt\\txt
.\data-mine.sh C:\\out2 C:\files