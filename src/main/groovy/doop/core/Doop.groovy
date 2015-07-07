package doop.core

import org.apache.log4j.Logger

/**
 * Doop initialization and supported options.
 *
 * @author: Kostas Saidis (saiko@di.uoa.gr)
 * Date: 9/7/2014
 */
class Doop {

    static final List<AnalysisOption> ANALYSIS_OPTIONS = [
        //LogicBlox related options (supporting different LogicBlox instance per analysis)
        new AnalysisOption<String>(
            id:"LOGICBLOX_HOME",
            name:"lbhome",
            argName:"PATH",
            description:"set the path to LogicBlox home (default: the value of the LOGICBLOX_HOME environment variable).",
            value:System.getenv("LOGICBLOX_HOME"),
            cli:true,
            webUI:false,
            isAdvanced:true
        ),
        new AnalysisOption<String>(
            id:"LD_LIBRARY_PATH", //the value is set based on LOGICBLOX_HOME
            value:null
        ),
        new AnalysisOption<String>(
            id:"BLOXBATCH", //the value is set based on LOGICBLOX_HOME
            value:null
        ),
        new AnalysisOption<String>(
            id:"BLOX_OPTS",
            value:null
        ),
        //Main options
        new AnalysisOption<String>(
            id:"MAIN_CLASS",
            name:"main",
            argName:"mainClass",
            description:"Specify the main class.",
            value:null,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<List<String>>(
            id:"DYNAMIC",
            name:"dynamic",
            argName:"FILE",
            isFile:true,
            description:"File with tab-separated data for Config:DynamicClass. Separate multiple files with a space.",
            value:[],
            cli:true,
            webUI:true
        ),
        new AnalysisOption<String>(
            id:"TAMIFLEX",
            name:"tamiflex",
            argName:"FILE",
            isFile:true,
            description:"Use file with tamiflex data for reflection.",
            value:null,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<String>(
            id:"CLIENT_CODE",
            name:"client",
            argName:"FILE",
            isFile:true,
            description:"Additional directory/file of client analysis to include.",
            value:null,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<String>(
            id:"CLIENT_EXTENSIONS",
            value:false,
            forPreprocessor:true
        ),
        /* Flags for must analyses */
        new AnalysisOption<String>(
            id:"MAY_PRE_ANALYSIS",
            name:"may-pre-analysis",
            argName:"may-analysis name",
            description:"Use a may analysis before running the must analysis.",
            value:null,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"MUST_AFTER_MAY",
            value:false,
            forPreprocessor:true
        ),
        
        /* Start of preprocessor constant flags */
        new AnalysisOption<Boolean>(
            id:"DISTINGUISH_ALL_STRING_CONSTANTS",
            name:"distinguish-all-string-constants",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true,
            flagType:PreprocessorFlag.CONSTANT_FLAG
        ),        
        new AnalysisOption<Boolean>(
            id:"DISTINGUISH_NO_STRING_CONSTANTS",
            name:"distinguish-no-string-constants",
            value:true,
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true,
            flagType:PreprocessorFlag.CONSTANT_FLAG
        ),
        /* End of preprocessor constant flags] */

        /* Start of preprocessor normal flags] */
        new AnalysisOption<String>(
            id:"NO_MODELING_OF_NUMS_OR_NULL",
            value:true, // enabled by default in run script
            forPreprocessor:true
        ),
        new AnalysisOption<Boolean>(
            id:"MERGE_STRING_BUFFERS",
            name:"disable-merge-string-buffers",
            value:true, //enabled by default in run script
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true
        ),
        new AnalysisOption<String>(
            id:"INCLUDE_IMPLICITLY_REACHABLE_CODE",
            name:"exclude-implicitly-reachable-code",
            value:true,  // enabled by default in run script
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"PADDLE_COMPAT",
            name:"paddle-compat",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),        
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_FILTER",
            name:"enable-exceptions-filter",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_ORDER",
            name:"enable-exceptions-order",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_RANGE",
            name:"enable-exceptions-range",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_CS",
            name:"enable-exceptions-cs",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"CLIENT_EXCEPTION_FLOW",
            name:"enable-exception-flow",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),        

        new AnalysisOption<Boolean>(
            id:"ENABLE_REFLECTION",
            name:"enable-reflection",
            description:"Enable advanced logic for handling Java reflection.",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true
        ),
        new AnalysisOption<Boolean>(
            id:"DISTINGUISH_REFLECTION_ONLY_STRING_CONSTANTS",
            name:"distinguish-reflection-only-string-constants",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true,
            flagType:PreprocessorFlag.CONSTANT_FLAG
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_MERGE_MEMBER_CONSTANTS",
            name:"enable-reflection-merge-member-constants",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_STRING_FLOW_ANALYSIS",
            name:"enable-reflection-string-flow-analysis",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_SUBSTRING_ANALYSIS",
            name:"enable-reflection-substring-analysis",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_CONTEXT_SENSITIVITY",
            name:"enable-reflection-context-sensitivity",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_USE_BASED_ANALYSIS",
            name:"enable-reflection-use-based-analysis",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor: true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_INVENT_UNKNOWN_OBJECTS",
            name:"enable-reflection-invent-unknown-objects",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"REFLECTION_REFINED_OBJECTS",
            name:"enable-reflection-refined-objects",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),

        new AnalysisOption<Boolean>(
            id:"NO_CONTEXT_REPEAT",
            name:"no-context-repeat",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"TRANSFORM_INPUT",
            name:"transform-input",
            description:"Transform input by removing redundant instructions.",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        /* End of preprocessor normal flags] */
        
        /* Start of preprocessor exception flags] */        
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_PRECISE",
            value:true, // enabled by default in run script
            forPreprocessor:true,
            flagType:PreprocessorFlag.EXCEPTION_FLAG
        ),
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_IMPRECISE",
            name:"enable-imprecise-exceptions",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true,
            flagType:PreprocessorFlag.EXCEPTION_FLAG
        ),
        new AnalysisOption<Boolean>(
            id:"SEPARATE_EXCEPTION_OBJECTS",
            value:false,
            forPreprocessor:true,
            flagType:PreprocessorFlag.EXCEPTION_FLAG
        ),
        new AnalysisOption<Boolean>(
            id:"EXCEPTIONS_EXPERIMENTAL",
            name:"enable-exceptions-experimental",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true,
            flagType:PreprocessorFlag.EXCEPTION_FLAG
        ),
        /* End of preprocessor exception flags] */        

        //other options/flags
        new AnalysisOption<Boolean>(
            id:"DISABLE_PRECISE_EXCEPTIONS",
            name:"disable-precise-exceptions",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:false,
            isAdvanced:true
        ),        
        new AnalysisOption<Boolean>(
            id:"DISABLE_MERGE_EXCEPTIONS",
            name:"disable-merge-exceptions",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:false,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"CSV",
            value:false,
        ),
        new AnalysisOption<Boolean>(
            id:"REFINE",
            value:false
        ),
        new AnalysisOption<Boolean>(
            id:"SSA",
            name:"ssa",
            description:"Use ssa transformation for input.",
            value:false,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"CACHE",
            name:"cache",
            description:"The analysis will use the cached facts, if they exist.",
            value:false,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"FULL_STATS",
            name:"full-stats",
            description:"Load additional logic for collecting statistics.",
            value:false,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"NO_STATS",
            name:"no-stats",
            description:"Do not load logic for collecting statistics.",
            value:false,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"SANITY",
            name:"sanity",
            description:"Load additional logic for sanity checks.",
            value:false,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"RUN_JPHANTOM",
            name:"run-jphantom",
            description:"Run jphantom for non-existent referenced jars.",
            value:false,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"AVERROES",
            name:"averroes",
            description:"Use averroes tool to create a placeholder library.",
            value:false,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"DACAPO",
            name:"dacapo",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<Boolean>(
            id:"DACAPO_BACH",
            name:"dacapo-bach",
            value:false,
            cli:true,
            webUI:true,
            forPreprocessor:true,
            isAdvanced:true
        ),
        new AnalysisOption<String>(
            id:"DACAPO_BENCHMARK",
            value:null,
            forPreprocessor:true
        ),
        new AnalysisOption<Boolean>(
            id:"DACAPO_2009",
            value:false,
            forPreprocessor:true
        ),
        new AnalysisOption<Boolean>(
            id:"USE_ORIGINAL_NAMES",
            name:"use-original-names",
            value:false,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<Boolean>(
            id:"ONLY_APPLICATION_CLASSES_FACT_GEN",
            name:"only-application-classes-fact-gen",
            value:false,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<String>(
            id:"SOOT",
            name:"soot",
            argName:"SOOT_VERSION",
            description:"One 2.5.0, 2.5.0-custom (default: 2.5.0).",
            value:"2.5.0",
            cli:true,
            webUI:true
        ),
        new AnalysisOption<String>( //Generates the properly named JRE option at runtime
            id:"JRE",
            name:"jre",
            argName:"VERSION",
            description:"One of 1.3, 1.4, 1.5, 1.6, 1.7, system (default: system).",
            value:"system",
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<OS>(
            id:"OS",
            value:OS.OS_UNIX
        ),
        new AnalysisOption<Boolean>(
            id:"INCREMENTAL",
            name:"incremental",
            value:false,
            cli:true,
            webUI:true,
            isAdvanced:true
        ),
        new AnalysisOption<String>(
            id:"APP_REGEX",
            name:"regex",
            argName:"regex-expression",
            description:"A regex expression for the Java package names to be analyzed.",
            value:null,
            forIDGeneration:true,
            cli:true,
            webUI:true
        ),
        new AnalysisOption<String>(
            id:"EXTERNALS",
            name:"externals",
            description:"The path to doop externals",
            value:System.getenv("DOOP_EXTERNALS"),
            cli:true,
            webUI:false,
            isAdvanced:true
        )
    ]

    // Not the best pattern, but limits the source code size :)
    static String doopHome
    static String doopLogic
    static String doopOut
    static String doopInputCache

    /**
     * Initializes Doop.
     * @param homePath The doop home directory (sets the doopHome variable, required).
     * @param outPath  The doop out directory (sets the doopOut variable, optional, defaults to doopHome/out).
     */
    static void initDoop(String homePath, String outPath) {

        //Check doopHome
        doopHome = homePath
        Helper.checkDirectoryOrThrowException(doopHome, "DOOP_HOME environment variable is invalid: $doopHome ")

        doopLogic = "$doopHome/logic"

        if (outPath) {
            doopOut = outPath
        }
        else {
            doopOut = "$doopHome/out"
        }

        //create all necessary files/folders
        File f = new File(doopOut)
        f.mkdirs()
        Helper.checkDirectoryOrThrowException(f, "Could not create ouput directory: $doopOut ")
    }

    /**
     * Creates the default analysis options.
     * @return Map<String, AnalysisOptions>.
     */
    static Map<String, AnalysisOption> createDefaultAnalysisOptions() {
        Map<String, AnalysisOption> options = [:]
        ANALYSIS_OPTIONS.each { AnalysisOption option ->
            options.put(option.id, AnalysisOption.newInstance(option))
        }
        return options
    }

    /**
     * Overrides the values of the map (the options values) with the values contained in the properties.
     * An option is set only if filtered (the supplied filter returns true for the option).
     * @param options - the options to override.
     * @param properties - the properties to use.
     * @param filter - the filter to apply.
     * @return the original map of options with its values overridden by the ones contained in the properties.
     */
    static void overrideOptionsWithProperties(Map<String, AnalysisOption> options,
                                              Properties properties,
                                              Closure<Boolean> filter) {
        if (properties && properties.size() > 0) {
            properties.each { Map.Entry<String, String> entry->
                AnalysisOption option = options.get(entry.key.toUpperCase())
                if (option && entry.value && entry.value.trim().length() > 0) {
                    boolean filtered = filter ? filter.call(option) : true
                    if (filtered) {
                        setOptionFromProperty(option, entry.value)
                    }
                }
            }
        }
    }

    /**
     * Creates the analysis options by overriding the default options with the ones contained in the given properties.
     * An option is set only if filtered (the supplied filter returns true for the option).
     * @param props - the properties.
     * @param filter - optional filter to apply before setting the option.
     * @return the default analysis options overridden by the values contained in the properties.
     */
    static Map<String, AnalysisOption> overrideDefaultOptionsWithProperties(Properties properties, Closure<Boolean> filter) {
        Map<String, AnalysisOption> options = createDefaultAnalysisOptions()
        overrideOptionsWithProperties(options, properties, filter)
        return options
    }

    /**
     * Creates the analysis options by processing the given properties.
     * An option is created only if filtered (the supplied filter returns true for the option).
     * @param props - the properties.
     * @param filter - optional filter to apply before setting the option.
     * @return the analysis options constructed by the values contained in the properties.
     */
    static Map<String, AnalysisOption> createOptionsFromProperties(Properties properties, Closure<Boolean> filter) {
        Map<String, AnalysisOption> options = [:]
        if (properties && properties.size() > 0) {
            ANALYSIS_OPTIONS.each { AnalysisOption option ->
                String property = properties.getProperty(option.id.toLowerCase())?.trim()
                if (property) {
                    boolean filtered = filter ? filter.call(option) : true
                    if (filtered) {
                        AnalysisOption o = AnalysisOption.newInstance(option)
                        setOptionFromProperty(o, property)
                        options.put(o.id, o)
                    }
                }
            }
        }
        return options
    }

    static void setOptionFromProperty(AnalysisOption option, String property) {
        if (option.id == "DYNAMIC") {
            option.value = property.split().collect { String s -> s.trim() }
        } else if (option.argName) {
            option.value = property
        } else {
            option.value = Boolean.parseBoolean(property)
        }
    }

    /**
     * Overrides the values of the map (the options values) with the values contained in the CLI options.
     * An option is set only if filtered (the supplied filter returns true for the option).
     * @param options - the options to override.
     * @param properties - the properties to use.
     * @param filter - the filter to apply.
     * @return the original map of options with its values overridden by the ones contained in the CLI options.
     */
    static void overrideOptionsWithCLI(Map<String, AnalysisOption> options, OptionAccessor cli, Closure<Boolean> filter) {
        options.values().each { AnalysisOption option ->
            String optionName = option.name
            if (optionName) {
                def optionValue = cli[(optionName)]
                Logger.getRootLogger().debug "Processing $optionName"
                if (optionValue) { //Only true-ish values are of interest (false or null values are ignored)
                    boolean filtered = filter ? filter.call(option) : true
                    if (filtered) {
                        setOptionFromCLI(option, cli)
                    }
                }
            }
        }
    }

    /**
     * Creates the analysis options by overriding the default options with the ones contained in the given CLI options.
     * An option is set only if filtered (the supplied filter returns true for the option).
     * @param cli - the CLI option accessor.
     * @param filter - optional filter to apply before setting the option.
     * @return the default analysis options overridden by the values contained in the CLI option accessor.
     */
    static Map<String, AnalysisOption> overrideDefaultOptionsWithCLI(OptionAccessor cli, Closure<Boolean> filter) {
        Map<String, AnalysisOption> options = createDefaultAnalysisOptions()
        overrideOptionsWithCLI(options, cli, filter)
        return options
    }

    static Map<String, AnalysisOption> createOptionsFromCLI(OptionAccessor cli, Closure<Boolean> filter) {
        Map<String, AnalysisOption> options = [:]
        ANALYSIS_OPTIONS.each { AnalysisOption option ->
            String optionName = option.name
            if (option.name) {
                def optionValue = cli.getProperty(optionName)
                if (optionValue) { //Only true-ish values are of interest (false or null values are ignored)
                    boolean filtered = filter ? filter.call(option) : true
                    if (filtered) {
                        AnalysisOption o = AnalysisOption.newInstance(option)
                        setOptionFromCLI(o, cli)
                        options.put(o.id, o)
                    }
                }
            }
        }
        return options
    }

    static void setOptionFromCLI(AnalysisOption option, OptionAccessor cli) {
        //Obscure cli builder feature: to get the value of a cl option as a List, you need to append an s to its short name
        if (option.id == "DYNAMIC") {
            //the short name of the DYNAMIC option is d, so we invoke ds
            option.value = cli.ds
        }
        else if (option.argName) {
            //if the cl option has an arg, the value of this arg defines the value of the respective
            // analysis option
            option.value = cli[(option.name)]
        } else {
            //the cl option has no arg and thus it is a boolean flag, toggling the default value of
            // the respective analysis option
            option.value = !option.value
        }
    }
}
