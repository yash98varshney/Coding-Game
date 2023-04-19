package com.crio.codingame.services;

//import java.util.Collections;
import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;

import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
        int size=userRepository.findAll().size()+1;
        User newUser=new User(Integer.toString(size),name,0);
        userRepository.save(newUser);
        return newUser;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){
        List<User> allUsers=userRepository.findAll();
        if(scoreOrder== ScoreOrder.ASC){
            allUsers.sort((User a ,User b)->{
                if(a.getScore()>b.getScore()){
                    return 1;
                }
                else if(a.getScore()<b.getScore()){
                    return -1;
                }
                else{
                    return 0;
                }
            });
            return allUsers;
        }
        else{
            allUsers.sort((User a ,User b)->{
                if(a.getScore()>b.getScore()){
                    return -1;
                }
                else if(a.getScore()<b.getScore()){
                    return 1;
                }
                else{
                    return 0;
                }
            });
            return allUsers;
        }
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is already registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {

        Optional<Contest> check=contestRepository.findById(contestId);
        
        
        if((Object)check ==Optional.empty()){
            throw new ContestNotFoundException();
        }

        Optional<User> usercheck=userRepository.findByName(userName);

        if((Object)usercheck==Optional.empty()){
            throw new UserNotFoundException();
        }

        Contest con=check.get();
        User user=usercheck.get();

        if(con.getContestStatus()==ContestStatus.ENDED){
            throw new InvalidOperationException();
        }

        if(con.getContestStatus()==ContestStatus.IN_PROGRESS){
            throw new InvalidOperationException();
        }

        if(con.getCreator().getName().equals(userName)){
            throw new InvalidOperationException();
        }

        if(!user.checkIfContestExists(con)){
            throw new InvalidOperationException();
        }

        user.deleteContest(con);
        userRepository.delete(user);
        userRepository.save(user);

        UserRegistrationDto widthdrawUser=null;
        
        widthdrawUser=new UserRegistrationDto(con.getName(),userName,RegisterationStatus.NOT_REGISTERED);
        return widthdrawUser;
  

    }
    
}
