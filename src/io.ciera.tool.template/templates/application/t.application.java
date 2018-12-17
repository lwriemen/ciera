package ${self.package};

${imports}

public class ${self.name} implements IApplication {

    private IComponent<?>[] components;
    private IRunContext[] executors;

    public ${self.name}() {
        components = new IComponent<?>[$t{num_component_instances}];
        executors = new IRunContext[$t{num_executors}];
    }

    @Override
    public void setup( String[] args, ILogger logger ) {
        for ( int i = 0; i < executors.length; i++ ) {
            if ( null != logger ) {
                executors[i] = new ApplicationExecutor( "${self.name}Executor" + i, args, logger );
            }
            else {
                executors[i] = new ApplicationExecutor( "${self.name}Executor" + i, args );
            }
        }
${component_instantiations}${component_satisfactions}    }

    @Override
    public void initialize() {
        for ( IComponent<?> component : components ) {
            component.getRunContext().execute( new GenericExecutionTask() {
                @Override
                public void run() throws XtumlException {
                    component.initialize();
                }
            });
        }
    }

    @Override
    public void start() {
        for ( IRunContext executor : executors ) {
            executor.start();
        }
    }

    @Override
    public void printVersions() {
        io.ciera.runtime.Version.printVersion();
        for ( IComponent<?> c : components ) {
            System.out.printf("%s: %s (%s)", c.getClass().getName(), c.getVersion(), c.getVersionDate());
            System.out.println();
        }
    }

    @Override
    public void stop() {
        for ( IRunContext executor : executors ) {
            executor.execute(new HaltExecutionTask());
        }
    }

    public static void main( String[] args ) {
        IApplication app = new ${self.name}();
        app.setup( args, null );
        if ( Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version") ) {
            app.printVersions();
        }
        else {
            app.initialize();
            app.start();
        }
    }

}
