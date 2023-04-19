package com.crio.codingame.commands;

import java.util.List;

import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.Level;
import com.crio.codingame.exceptions.QuestionNotFoundException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.services.IContestService;

public class CreateContestCommand implements ICommand{

    private final IContestService contestService;

    public CreateContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    // TODO: CRIO_TASK_MODULE_CONTROLLER
    // Execute create method of IContestService and print the result.
    // Also Handle Exceptions and print the error messsages if any.
    // Look for the unit tests to see the expected output.
    // Sample Input Token List:- ["CREATE_CONTEST","CRIODO2_CONTEST","LOW Monica","40"]
    // or
    // ["CREATE_CONTEST","CRIODO1_CONTEST","HIGH","Ross"]
    // Hint - Use Parameterized Exceptions in the Service class to match with the Unit Tests Output.
    //create(String contestName, Level level, String contestCreator, Integer numQuestion) throws UserNotFoundException, QuestionNotFoundException;

    @Override
    public void execute(List<String> tokens) {
        String contestName=tokens.get(1);
        String levelString=tokens.get(2);
        Level level=null;
        switch(levelString){
            case "LOW":
                level=Level.LOW;
                break;
            case "MEDIUM":
                level=Level.MEDIUM;
                break;
            case "HIGH":
                level=Level.HIGH;
                break;
            default:
                break;

        }
        String contestCreator=tokens.get(3);
        String numberOfQuestions="";
        if(tokens.size()==5){
            numberOfQuestions=tokens.get(4);
        }
        
        try{
            Contest created;
            if(numberOfQuestions.equals("")){
                created=contestService.create(contestName, level, contestCreator, null);
            }
            else{
                created=contestService.create(contestName, level, contestCreator, Integer.valueOf(numberOfQuestions));
            }
            
            System.out.println(created);
        }
        catch(QuestionNotFoundException e){
           System.out.println("Request cannot be processed as enough number of questions can not found. Please try again later!");
        }
        catch(UserNotFoundException e){
            System.out.println("Contest Creator for given name: creator not found!");
        }

    }

    
}
