package net.avalith.elections.service;

import net.avalith.elections.models.Election;

import java.util.List;

public interface IElectionService {
	public List<Election> findAll();

	public Election save(Election election);

	public Election findById(Long id);

	public void delete(Long id);

}
