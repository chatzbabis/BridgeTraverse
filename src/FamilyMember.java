public class FamilyMember {

    private String name;
    private int time;

    public FamilyMember(String name, int time){

        this.name = name;
        this.time = time;

    }

    public void setName(String name){

        this.name = name;
    }

    public String getName(){

        return name;
    }

    public void setTime(int time){

        this.time = time;
    }

    public int getTime(){

        return time;
    }

}
