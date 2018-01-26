package fi.metropolia.juhavuo.chatserver;

import java.util.ArrayList;

/**
 * This is for keeping track of users.
 * @author Juha Vuokko
 * @version 1.0
 */
public class UserNameList {

    private ArrayList<String> userNameList;

    private static UserNameList usedList = new UserNameList();
    private  UserNameList(){
        this.userNameList = new ArrayList<>();
    }

    public static UserNameList getInstance(){
        return usedList;
    }

    public void insert(String name){
        this.userNameList.add(name);
    }

    public void remove(String name){
        this.userNameList.remove(name);
    }

    public boolean contains(String name){
        return this.userNameList.contains(name);
    }

    @Override
    public String toString(){
        StringBuilder names = new StringBuilder();
        for (int i = 0;i<userNameList.size();++i){
            names.append(userNameList.get(i));
            if(i<userNameList.size()-1){
                names.append("\r\n");
            }
        }
        return names.toString();
    }

    public int getSize(){
        return userNameList.size();
    }
}
