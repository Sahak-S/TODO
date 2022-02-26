package todo;

public interface Commands {

    String EXIT = "0";
    String LOGIN = "1";
    String REGISTER = "2";

    String LOGOUT = "0";
    String ADD_NEW_TODO = "1";
    String MY_ALL_LIST = "2";
    String MY_TODO_LIST = "3";
    String MY_IN_PROGRESS_LIST = "4";
    String MY_FINISHED_LIST = "5";
    String CHANGE_TODO_STATUS = "6";
    String DELETE_TO_DO = "7";

    static void printMainCommands() {
        System.out.println("Please input " + EXIT + " for EXIT");
        System.out.println("Please input " + LOGIN + " for LOG IN");
        System.out.println("Please input " + REGISTER + " for REGISTER ");

    }


    static void printUseCommand() {
        System.out.println("Please input " + EXIT + "EXIT");
        System.out.println("Please input " + ADD_NEW_TODO + "for ADD_NEW_TODO");
        System.out.println("Please input " + MY_ALL_LIST + " for MY_ALL_LIST");
        System.out.println("Please input " + MY_TODO_LIST + " for MY_TODO_LIST");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " for MY_IN_PROGRESS_LIST");
        System.out.println("Please input " + MY_FINISHED_LIST + " for MY_FINISHED_LIST");
        System.out.println("Please input " + CHANGE_TODO_STATUS + " for CHANGE_TODO_STATUS");
        System.out.println("Please input " + DELETE_TO_DO + " for DELETE_TO_DO");
    }
}
