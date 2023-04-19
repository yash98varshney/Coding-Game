package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.services.IContestService;

public class ListContestCommand implements ICommand{

    private final IContestService contestService;
    
    public ListContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute getAllContestLevelWise method of IContestService and print the result.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["LIST_CONTEST","HIGH"]
    // or
    // ["LIST_CONTEST"]
    //getAllContestLevelWise(Level level);

    @Override
    public void execute(List<String> tokens) {

        if(tokens.size()==1){
            List<Contest> contestList=contestService.getAllContestLevelWise(null);
            System.out.println(contestList);
        }
        else{
            String lvl=tokens.get(1);
            Level level=null;
            switch(lvl){
                case "HIGH":
                    level=Level.HIGH;
                    break;
                case "LOW":
                    level=Level.LOW;
                    break;
                case "MEDIUM":
                    level=Level.MEDIUM;
                    break;
                default:
                    break;
            }

            List<Contest> contestList=contestService.getAllContestLevelWise(level);
            System.out.println(contestList);
        }

    }
    
}
