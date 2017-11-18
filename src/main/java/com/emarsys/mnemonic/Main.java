package com.emarsys.mnemonic;

import com.emarsys.mnemonic.miner.MinerImpl;
import com.emarsys.mnemonic.miner.MinerFactory;
import com.emarsys.mnemonic.service.OutputTransformer;
import com.emarsys.mnemonic.service.PhoneMnemonicService;
import com.emarsys.mnemonic.service.ServiceFactory;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String INDEX_DIRECTORY = "indexDirectory";
    private static final String SOURCE_DIRECTORY = "sourceDirectory";
    private static final String PHONE_NUMBERS = "phoneNumbers";

    private static OutputTransformer outputTransformer = new OutputTransformer();
    private static MinerFactory minerFactory = new MinerFactory();
    private static ServiceFactory serviceFactory = new ServiceFactory();

    public static void main(String[] args) throws IOException {

        Options options = new Options();

        Option task = new Option("t", "task", true, "Task to perform [mine / service]");
        task.setRequired(true);
        options.addOption(task);

        Option sourceDirectory = new Option("s", SOURCE_DIRECTORY, true, "Location of files to index");
        options.addOption(sourceDirectory);

        Option indexDirectory = new Option("i", INDEX_DIRECTORY, true, "Location to save index file");
        options.addOption(indexDirectory);

        Option phoneNumbersOption = new Option("p", PHONE_NUMBERS, true, "List of phone numbers");
        options.addOption(phoneNumbersOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("phone mnemonic", options);

            System.exit(1);
            return;
        }

        String indexDirectoryArgumentValue = cmd.getOptionValue(INDEX_DIRECTORY);
        String sourceDirectoryArgumentValue = cmd.getOptionValue(SOURCE_DIRECTORY);

        String taskToPerform = cmd.getOptionValue("task");
        if (taskToPerform.equalsIgnoreCase("mine")) {
            MinerImpl miner = minerFactory.create(indexDirectoryArgumentValue, sourceDirectoryArgumentValue);
            miner.mine();

        } else if (taskToPerform.equalsIgnoreCase("service")) {

            if (!cmd.hasOption(PHONE_NUMBERS))
                return;

            List<String> phoneNumbers = Arrays.asList(cmd.getOptionValue(PHONE_NUMBERS).split(" "));

            PhoneMnemonicService service = serviceFactory.create(indexDirectoryArgumentValue);
            System.out.println(outputTransformer.transform(service.getMnemonicsForPhoneNumbers(phoneNumbers)));
        }
    }
}
