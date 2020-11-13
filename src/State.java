import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


    public class State implements Comparable<State>
    {
        private int n;  // family members number
        private boolean lamp; // lamp
        private ArrayList<FamilyMember> rob; //right of bridge
        private ArrayList<FamilyMember> lob; //left of bridge
        private FamilyMember cat; // kai h gata einai melos ths oikogeneias
        private FamilyMember kid;
        private FamilyMember father;
        private FamilyMember mother;
        private FamilyMember grandma;

        /*Afta ta melh xrisimopoiountai mono sthn ylopoihsh me tyxaio
                            arithmo n*/

        private FamilyMember grandpa;
        private FamilyMember aunt;
        private FamilyMember uncle;
        private FamilyMember cousin;
        private FamilyMember dog;

        private int score; // to kostos tou path mexri to state n, g(n)
        private int heuristicScore; //to score ths heuristic gia to state n, h(n)
        private int totalScore; // to athroisma g(n) + h(n)
        private State state_father=null;

        public State(int n){    // constructor gia thn ylopoihsh me tyxaio n (apo 2 ews 10) kai tyxaious xronous ana atomo

            Random r = new Random();
            ArrayList<FamilyMember> temp = new ArrayList<FamilyMember>();

            this.n = n;

            /*xrhsimopoioume th random gia na kanoume assign mia tyxaia timh apo 1 ews 15 gia to kathe atomo
                kai ta vazoume se mia proswrinh lista wste na thn kanoume shuffle kai na epileksoume tyxaia n apo auta */

            cat = new FamilyMember("Cat", 1 + r.nextInt(14));          temp.add(cat);
            kid = new FamilyMember("Kid", 1 + r.nextInt(14));          temp.add(kid);
            father = new FamilyMember("Father", 1 + r.nextInt(14));    temp.add(father);
            mother = new FamilyMember("Mother", 1 + r.nextInt(14));    temp.add(mother);
            grandma = new FamilyMember("Grandma", 1 + r.nextInt(14));  temp.add(grandma);
            grandpa = new FamilyMember("Grandpa", 1 + r.nextInt(14));  temp.add(grandpa);
            aunt = new FamilyMember("Aunt", 1 + r.nextInt(14));        temp.add(aunt);
            uncle = new FamilyMember("Uncle", 1 + r.nextInt(14));      temp.add(uncle);
            cousin = new FamilyMember("Cousin", 1 + r.nextInt(14));    temp.add(cousin);
            dog = new FamilyMember("Dog", 1 + r.nextInt(14));          temp.add(dog);

            Collections.shuffle(temp);

            this.rob = new ArrayList<FamilyMember>();
            this.lob = new ArrayList<FamilyMember>();

            for (int i = 0; i < n; i++){

                this.rob.add(temp.get(i));
            }

            lamp = false;

            score = 0;

        }

        public State() //constructor gia thn ylopoihsh me n = 5 kai xronous aftous pou mas exoun dwthei sto paradeigma
        {

            this.n = 5;
            cat = new FamilyMember("Cat", 1);
            kid = new FamilyMember("Kid", 3);
            father = new FamilyMember("Father", 6);
            mother = new FamilyMember("Mother", 8);
            grandma = new FamilyMember("Granmda", 12);

            this.rob = new ArrayList<FamilyMember>();
            this.lob = new ArrayList<FamilyMember>();

            this.rob.add(cat);
            this.rob.add(kid);
            this.rob.add(father);
            this.rob.add(mother);
            this.rob.add(grandma);

            lamp = false;

            score = 0;
        }

        public State(State s) /* constructor gia thn paragwgh paidiou, antigrafoume sto child-to-be state ton patera
                                    panomoiotypo, prin ton epeksergastoume k parei th morfh paidiou */
        {
            this.n = s.getNum();
            this.score = s.getScore();
            this.lamp = s.getLamp();
            this.rob = new ArrayList<FamilyMember>();
            this.lob = new ArrayList<FamilyMember>();

            for(int i=0; i<s.rob.size(); i++)
                rob.add(s.getRobMember(i));

            for(int i=0; i<s.lob.size(); i++)
                lob.add(s.getLobMember(i));

        }

        public int getNum(){

            return n;
        }

        public FamilyMember getRobMember(int i) {

            return rob.get(i);
        }

        public FamilyMember getLobMember(int i){

            return lob.get(i);
        }

        public boolean getLamp()
        {
            return lamp;
        }

        public int getScore()
        {
            return this.score;
        }

        public int getHeuristicScore(){

            return heuristicScore;
        }

        public int getTotalScore(){

            return totalScore;
        }


        public void changeScore(int score) // auksanoume to score logw metakinhshs
        {
            this.score += score;
        }

        private void changeHeuristicScore() { /* h heuristic synarthsh mas, vriskei ton max xrono apo ta atoma ta opoia
                                                       exoun apomeinei deksia ths gefyras */
            heuristicScore = rob.get(0).getTime();

            for (FamilyMember f: rob){

                if (f.getTime() > heuristicScore)
                    heuristicScore = f.getTime();
            }
        }

        private void changeTotalScore(){ // ypologizoume to total kostos gia to state mas

            totalScore = getScore() + getHeuristicScore();
        }

        public void moveLamp(){ // metakinoume th lampa sthn apenanti plevra

            this.lamp = !this.lamp;
        }


        public void moveToRob(FamilyMember fm) /* metakinhsh atomou sth deksia plevra ths gefyras. thewroume oti
                                                            pros ta deksia kineitai panta mono enas*/
        {
            rob.add(lob.remove(lob.indexOf(fm)));
            changeScore(fm.getTime());
            changeHeuristicScore();
            changeTotalScore();
            moveLamp();

        }

        public void moveToLob(FamilyMember fm1, FamilyMember fm2){  /* metakinhsh atomou sthn aristerh plevra ths gefyras. thewroume oti
                                                            pros ta aristera kinountai panta 2*/

            lob.add(rob.remove(rob.indexOf(fm1)));
            lob.add(rob.remove(rob.indexOf(fm2)));

            if (fm1.getTime() > fm2.getTime())
                changeScore(fm1.getTime());
            else
                changeScore(fm2.getTime());

            if (!rob.isEmpty())
                changeHeuristicScore();

            changeTotalScore();
            moveLamp();
        }


        public ArrayList<State> getChildren() // epistrefei ta states-paidia
        {
            ArrayList<State> children = new ArrayList<State>();
            State child = new State(this);
            if(child.getLamp()) {           //metakinhsh apo LoB pros RoB

                for (int i = 0; i < child.lob.size(); i++){

                    child.setFather(this);
                    child.moveToRob(child.lob.get(i));
                    children.add(child);
                    child = new State(this);

                }

            } else {        //metakinhsh apo RoB pros LoB

                for (int i = 0; i < child.rob.size(); i++) {

                    for (int j = i + 1; j < child.rob.size(); j++) {

                        child.setFather(this);
                        child.moveToLob(child.rob.get(i), child.rob.get(j));
                        children.add(child);
                        child = new State(this);
                    }

                }
            }

            return children;
        }

        public boolean isTerminal()         //einai terminal an ola t atoma exoun paei LoB, mazi me th lampa
        {
            if(!rob.isEmpty())
            {
                return false;
            }
            else if (lob.size() == n && lamp) {

                return true;
            }

            return false;
        }

        public void print()
        {
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            if (getLamp()) System.out.print("(Lamp Left)\t");

            for(FamilyMember f: lob){

                System.out.print(f.getName() + " " + f.getTime() + "\t\t");
            }

            System.out.print("_____________________________Bridge_____________________________\t");

            for(FamilyMember f: rob){

                System.out.print(f.getName() + " " + f.getTime() + "\t\t");
            }

            if (!getLamp()) System.out.print("(Lamp Right)\t");

            System.out.println();
        }

        @Override
        //We override the compareTo function of this class so only total scores are compared
        public int compareTo(State s)
        {
            return Double.compare(this.getTotalScore(), s.getTotalScore());
        }

        public State getFather() {
            return state_father;
        }

        public void setFather(State father) {
            this.state_father = father;
        }
    }
