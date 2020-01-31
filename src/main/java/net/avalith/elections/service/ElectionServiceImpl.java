package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionDao;
import net.avalith.elections.models.Election;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectionServiceImpl implements IElectionService {

    @Autowired
    private IElectionDao electionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Election> findAll() {
        return (List<Election>) electionDao.findAll();
    }

    @Override
    @Transactional
    public Election save(Election election) {
        return electionDao.save(election);
    }

    @Override
    @Transactional(readOnly = true)
    public Election findById(Long id) {
        return electionDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        electionDao.deleteById(id);

    }

}