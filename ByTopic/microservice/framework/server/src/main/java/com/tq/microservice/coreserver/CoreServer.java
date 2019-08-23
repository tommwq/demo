package com.tq.microservice.coreserver;

import com.tq.microservice.App;
import com.tq.microservice.annotation.Executable;
import com.tq.utility.CollectionUtil;
import com.tq.utility.Util;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@Executable
public class CoreServer {
  private static final String DEFAULT_CONFIG_FILE_NAME = "coreserver.properties";
  private static final String COMMANDLINE_OPTION_CONFIG_FILE = "config-file";

  public String parseConfigFileName(String... args) throws ParseException {
    Options options = new Options();
    options.addOption("COMMANDLINE_OPTION_CONFIG_FILE", false, "conig file path");

    CommandLineParser parser = new DefaultParser();
    CommandLine command = parser.parse(options, args);

    String configFileName = DEFAULT_CONFIG_FILE_NAME;
    if (command.hasOption("COMMANDLINE_OPTION_CONFIG_FILE")) {
      configFileName = command.getOptionValue("COMMANDLINE_OPTION_CONFIG_FILE");
    }

    return configFileName;
  }

  public CoreServerConfig readConfig(String configFileName)
    throws IOException, InstantiationException, IllegalAccessException {

    try(final FileInputStream inputStream = new FileInputStream(configFileName)) {
      Properties properties = new Properties();
      properties.load(inputStream);

      CoreServerConfig config = CollectionUtil.propertiesToObject(properties, CoreServerConfig.class);
      return config;
    }
  }
  
  public void execute(String... args) {
    try {
      System.out.println(args.length);
      CoreServerConfig config = readConfig(parseConfigFileName(args));
      

      System.out.println(config.gateway.toString());
      System.out.println("*************\nok\n*************\n");
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  public static void main(String... args) {
    System.out.println(args.length);
    App.main(args);
  }
}
