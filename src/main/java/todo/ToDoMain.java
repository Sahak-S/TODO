package todo;

import todo.maneger.ToDoManager;
import todo.maneger.UserManager;
import todo.model.ToDo;
import todo.model.ToDoStatus;
import todo.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ToDoMain implements Commands {
    private static Scanner scanner = new Scanner(System.in);
    public static UserManager userManager = new UserManager();
    public static ToDoManager toDoManager = new ToDoManager();
    private static User currentUser = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");


    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            String command;
            try {
                command = scanner.nextLine();
            } catch (NumberFormatException e) {
                command = String.valueOf(-1);
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;

                default:
                    System.err.println("INVALID COMMAND");
            }
        }

    }

    private static void registerUser() {
        System.out.println("please input user data " + " name,surname,email,password");

        try {
            String userDataStr = scanner.nextLine();
            String[] userDataArr = userDataStr.split(",");
            User userFromManager = userManager.getByEmail(userDataArr[2]);
            if (userFromManager == null) {
                User user = new User();
                user.setName(userDataArr[0]);
                user.setSurname(userDataArr[1]);
                user.setEmail(userDataArr[2]);
                user.setPassword(userDataArr[3]);

                if (userManager.register(user)) {
                    System.out.println("User was successfully added");
                } else {
                    System.out.println("Somthing whent wrong");
                }
            } else {
                System.out.println("User already exist");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data");
        }

    }

    private static void loginUser() {
        System.out.println("Enter Your email, password");

        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User user = userManager.getByEmailAndPassword(loginArr[0], loginArr[1]);

            if (user != null) {
                currentUser = user;
                loginSuccess();
            } else {
                System.out.println("wrong email or password");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("wrong Data");
        }


    }

    private static void loginSuccess() {
        System.out.println("Welcom " + currentUser.getName() + " !");
        boolean isRun = true;
        while (isRun) {
            Commands.printUseCommand();
            String command;
            try {
                command = scanner.nextLine();
            } catch (NumberFormatException e) {
                command = String.valueOf(-1);
            }

            String startCommand = scanner.nextLine();

            switch (startCommand) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_NEW_TODO:
                    addNewToDo();
                case MY_ALL_LIST:
                    toDoManager.getAllToDosByUser(currentUser.getId());
                    break;
                case MY_TODO_LIST:
                    toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.TODO);
                    break;
                case MY_IN_PROGRESS_LIST:
                    toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.IN_PROGRESS);
                    break;
                case MY_FINISHED_LIST:
                    toDoManager.getAllToDosByUserAndStatus(currentUser.getId(), ToDoStatus.FINISHED);
                    break;
                case CHANGE_TODO_STATUS:
                    changeToDo();
                    break;
                case DELETE_TO_DO:
                    deleteToDo();
                    break;
                default:
                    System.err.println("INVALID COMMAND");
            }
        }

    }

    private static void addNewToDo() {
        System.out.println("pleas input title,deadline(yyyy-MM-dd:HH-mm-ss)");
        String toDoDataStr = scanner.nextLine();
        String[] split = toDoDataStr.split(",");
        ToDo toDo = new ToDo();
        try {
            String title = split[0];
            toDo.setTitle(title);
            try {
                if (split[1] != null) {
                    toDo.setDateline(sdf.parse(split[1]));
                }

            } catch (IndexOutOfBoundsException e) {
            } catch (ParseException e) {
                System.out.println("pleas input date by dhis format : yyyy-MM-dd:HH-mm-ss ");
                ;
            }
            toDo.setStatus(ToDoStatus.TODO);
            toDo.setUser(currentUser);
            if (toDoManager.create(toDo)) {
                System.out.println("ToDo wos added");
            } else {
                System.out.println("Something went wrong");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Wrong Data");
        }

    }

    private static void deleteToDo() {
        System.out.println("pleas choose TODO from list");
        List<ToDo> list = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : list) {
            System.out.println(toDo);
        }
        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            toDoManager.delete(id);
        } else {
            System.out.println("Wrong ID");
        }
    }

    private static void changeToDo() {
        System.out.println("pleas choose TODO from list:");
        List<ToDo> list = toDoManager.getAllToDosByUser(currentUser.getId());
        for (ToDo toDo : list) {
            System.out.println(toDo);
        }
        long id = Long.parseLong(scanner.nextLine());
        ToDo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            System.out.println("pleas choose Status");
            System.out.println(Arrays.toString(ToDoStatus.values()));
            ToDoStatus status = ToDoStatus.valueOf(scanner.nextLine());
            if (toDoManager.update(id, status)) {
                System.out.println("Status wos changed");
            } else {
                System.out.println("Something went wrong ");
            }
        } else {
            System.out.println("Wrong ID");
        }
    }

}
