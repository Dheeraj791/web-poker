package ar.com.tandilweb.orchestrator.persistence.repository;

import org.springframework.stereotype.Repository;

import ar.com.tandilweb.orchestrator.persistence.BaseRepository;
import ar.com.tandilweb.orchestrator.persistence.domain.Sessions;

@Repository
public class SessionsRepository extends BaseRepository<Sessions, Long> {

	@Override
	public Sessions create(Sessions record) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Sessions record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sessions findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
