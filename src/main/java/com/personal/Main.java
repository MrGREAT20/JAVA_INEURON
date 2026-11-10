package com.personal;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Department{
    private int id;
    private String name;

    public Department(int id , String name){
        this.id= id;
        this.name=name;
    }

    @Override
    public boolean equals(Object obj) { // if obj and current Object return same equals method, then there hascode should also be same
        if(obj instanceof Department fmtObj){
            return fmtObj.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

public class Main {
    static void main() {//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text


        Set<Department> set=new HashSet<>();
        // HashSet is just a wrapper around HashMap , where .add( e ) method internally adds "e" as Key and a dummy new Object() as value
        // on every add Set checks if this value is same as any of the previous values (by using .equals() and .hashCode() )
        set.add(new Department(1, "Dev"));
        set.add(new Department(1, "Dev"));

        System.out.println(set.size()); // my answer is 2
    }
}
