import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class Veigar {
    static List<Task> storage = SaveAndLoad.load();
    static boolean active = true;

    enum COMMAND {
        BYE {
            @Override
            void execute(String args) {
                System.out.println("    I shall return, better and BIGGER!!!");
                active = false;
            }
        },

        LIST {
            @Override
            void execute(String args) {
                System.out.println("    Suffering awaits!");
                if (storage.isEmpty()) {
                    System.out.println("    DON'T DEAD OPEN INSIDE LIST IS 0");
                    return;
                }
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println(((i + 1) + ". " + storage.get(i)).indent(4));
                }
            }
        },

        MARK {
            @Override
            void execute(String args) throws VeigarException {
                int n = parseIndex(args);
                System.out.println("    Your commands tire me.");
                storage.get(n).markDone();
                System.out.println(storage.get(n).toString().indent(4));
            }
        },

        UNMARK {
            @Override
            void execute(String args) throws VeigarException {
                int n = parseIndex(args);
                System.out.println("    No, no, NO! THIS ISN'T OVER!!!");
                storage.get(n).markUndone();
                System.out.println(storage.get(n).toString().indent(4));
            }
        },

        TODO {
            @Override
            void execute(String args) throws VeigarException {
                if (args.trim().isEmpty()) {
                    throw new VeigarException("You have nothing");
                }
                storage.add(new ToDo(args));
                afterAdd();
            }
        },

        EVENT {
            @Override
            void execute(String args) throws VeigarException {
                String[] parts = args.split(" /from | /to ");
                if (parts.length != 3) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                storage.add(new Event(parts[0], parts[1], parts[2]));
                afterAdd();
            }
        },

        DEADLINE {
            @Override
            void execute(String args) throws VeigarException {
                String[] parts = args.split("/by");
                if (parts.length != 2) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                storage.add(new Deadline(parts[0], parts[1]));
                afterAdd();
            }
        },

        DELETE {
            @Override
            void execute(String args) throws VeigarException {
                int n = parseIndex(args);
                System.out.println("    I have deleted this task:\n    " + storage.get(n));
                storage.remove(n);
                afterAdd();
            }
        },
        SHOW {
            @Override
            void execute(String args) throws VeigarException {
                String queryDate = args.trim(); // e.g. "1 Feb 2026"
                boolean found = false;

                for (int i = 0; i < storage.size(); i++) {
                    Task t = storage.get(i);
                    String taskDate = null;

                    if (t instanceof Deadline d) {
                        taskDate = d.getBy().split(",")[0].trim();
                    } else if (t instanceof Event e) {
                        taskDate = e.getToDate().split(",")[0].trim();
                    } else {
                        continue; // skip other task types
                    }

                    if (taskDate.equals(queryDate)) {
                        System.out.println("    " + (i + 1) + ". " + t);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("    No tasks found on " + queryDate);
                }

            }

        }


        ;
        abstract void execute(String args) throws VeigarException;
    }
    static int parseIndex(String s) throws VeigarException {
        try {
            tasks = new TaskList(storage.load());
        } catch (VeigarException e) {
            //ui.showLoadingError();
            tasks = new TaskList();
        }

    }    enum COMMAND {
        BYE {
            @Override
            void execute(String args) {
                System.out.println("    I shall return, better and BIGGER!!!");
                active = false;
            }
        },

        LIST {
            @Override
            void execute(String args) {
                System.out.println("    Suffering awaits!");
                if (storage.isEmpty()) {
                    System.out.println("    DON'T DEAD OPEN INSIDE LIST IS 0");
                    return;
                }
                for (int i = 0; i < storage.size(); i++) {
                    System.out.println(((i + 1) + ". " + storage.get(i)).indent(4));
                }
            }
        },

        MARK {
            @Override
            void execute(String args) throws VeigarException {
                int n = parseIndex(args);
                System.out.println("    Your commands tire me.");
                storage.get(n).markDone();
                System.out.println(storage.get(n).toString().indent(4));
            }
        },

        UNMARK {
            @Override
            void execute(String args) throws VeigarException {
                int n = parseIndex(args);
                System.out.println("    No, no, NO! THIS ISN'T OVER!!!");
                storage.get(n).markUndone();
                System.out.println(storage.get(n).toString().indent(4));
            }
        },

        TODO {
            @Override
            void execute(String args) throws VeigarException {
                if (args.trim().isEmpty()) {
                    throw new VeigarException("You have nothing");
                }
                storage.add(new ToDo(args));
                afterAdd();
            }
        },

        EVENT {
            @Override
            void execute(String args) throws VeigarException {
                String[] parts = args.split(" /from | /to ");
                if (parts.length != 3) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                storage.add(new Event(parts[0], parts[1], parts[2]));
                afterAdd();
            }
        },

        DEADLINE {
            @Override
            void execute(String args) throws VeigarException {
                String[] parts = args.split("/by");
                if (parts.length != 2) {
                    throw new VeigarException("INVALID NUMBER OF ARGS");
                }
                storage.add(new Deadline(parts[0], parts[1]));
                afterAdd();
            }
        },

        DELETE {
            @Override
            void execute(String args) throws VeigarException {
                int n = parseIndex(args);
                System.out.println("    I have deleted this task:\n    " + storage.get(n));
                storage.remove(n);
                afterAdd();
            }
        },
        SHOW {
            @Override
            void execute(String args) throws VeigarException {
                String queryDate = args.trim(); // e.g. "1 Feb 2026"
                boolean found = false;

                for (int i = 0; i < storage.size(); i++) {
                    Task t = storage.get(i);
                    String taskDate = null;

                    if (t instanceof Deadline d) {
                        taskDate = d.getBy().split(",")[0].trim();
                    } else if (t instanceof Event e) {
                        taskDate = e.getToDate().split(",")[0].trim();
                    } else {
                        continue; // skip other task types
                    }

                    if (taskDate.equals(queryDate)) {
                        System.out.println("    " + (i + 1) + ". " + t);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("    No tasks found on " + queryDate);
                }

            }

        }


        ;
        abstract void execute(String args) throws VeigarException;
    }
    static int parseIndex(String s) throws VeigarException {
        try {
            int n = Integer.parseInt(s) - 1;
            if (n < 0 || n >= storage.size()) {
                throw new VeigarException("Your commands are wrong");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new VeigarException("Please provide a valid number");
        }
    }

    static void afterAdd() {
        System.out.println("    Now you have " + storage.size() + " tasks in the list");
        SaveAndLoad.save(storage);
    }


    public static void main(String[] args) {
        //introduction
        System.out.println("I am VeigarBot \nHEHEHEHA");

        Scanner scanner = new Scanner(System.in);

        while (active) {
            String s = scanner.nextLine();
            String[] cmd = s.split(" ", 2);
            String commandArgs = cmd.length > 1 ? cmd[1] : "";
            try {
                COMMAND.valueOf(cmd[0].toUpperCase()).execute(commandArgs);
            } catch (IllegalArgumentException e) {
                System.out.println("    Whoops wrong command, Suffering awaits!");
            } catch (VeigarException e) {
                System.out.println("    " + e.getMessage());
            }

        }
    }


}


