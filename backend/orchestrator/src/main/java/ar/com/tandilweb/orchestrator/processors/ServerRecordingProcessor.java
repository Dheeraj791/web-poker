package ar.com.tandilweb.orchestrator.processors;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.tandilweb.exchange.ServerRecordingSchema;
import ar.com.tandilweb.exchange.roomAuth.Handshake;
import ar.com.tandilweb.exchange.serverRecording.Deposit;
import ar.com.tandilweb.exchange.serverRecording.InvalidDeposit;
import ar.com.tandilweb.exchange.serverRecording.SuccessDeposit;
import ar.com.tandilweb.orchestrator.persistence.repository.ChallengesRepository;
import ar.com.tandilweb.persistence.domain.Challenges;
import ar.com.tandilweb.persistence.domain.Users;
import ar.com.tandilweb.persistence.repository.UsersRepository;

@Service
public class ServerRecordingProcessor {
	
	@Autowired 
	ChallengesRepository challengeRepository;
	
	@Autowired
	UsersRepository userRepository;
	
	protected static Logger logger = LoggerFactory.getLogger(ServerRecordingProcessor.class);
	
	public ServerRecordingSchema deposit(String schemaBody, Handshake roomData) throws JsonParseException, JsonMappingException, IOException {
		logger.debug("Schema deposit");
		ObjectMapper om = new ObjectMapper();
		Deposit inputSchema = om.readValue(schemaBody, Deposit.class);
		try {
			Challenges challenge = challengeRepository.findById(inputSchema.challengeID);
			if (challenge == null) throw new Exception("Challenge is null");
			if (!challenge.getChallenge().equals(inputSchema.claimToken)) throw new Exception("Challenge not match with claim token");
			if (challenge.getId_user() != inputSchema.userID) throw new Exception("Challenge isn't for this user");
			if (challenge.getDeposit() == null) throw new Exception("This challenge isn't for deposit transaction");
			if (challenge.getDeposit().longValue() != inputSchema.chips) throw new Exception("The challenge doesn't match witt the chips user inform to deposit.");
			Users user = userRepository.findById(inputSchema.userID);
			if(user == null) throw new Exception("The user doesn't exists");
			if(user.getChips() < inputSchema.chips) throw new Exception("The user not have enough chips to deposit.");
			user.setChips(user.getChips() - inputSchema.chips);
			userRepository.update(user);
			challengeRepository.delete(challenge);
			SuccessDeposit sD = new SuccessDeposit();
			sD.depositedChips = inputSchema.chips;
			sD.chips = user.getChips();
			sD.userID = inputSchema.userID;
			return sD;
		} catch(Exception e) {
			logger.debug("This deposit are invalid because: ", e);
			InvalidDeposit iD = new InvalidDeposit();
			iD.userID = inputSchema.userID;
			return iD;
		}
	}

}
