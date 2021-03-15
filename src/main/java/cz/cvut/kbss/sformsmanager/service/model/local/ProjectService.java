package cz.cvut.kbss.sformsmanager.service.model.local;

import cz.cvut.kbss.sformsmanager.model.persisted.local.Project;
import cz.cvut.kbss.sformsmanager.persistence.dao.local.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    private final ProjectDAO projectDAO;

    @Autowired
    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public List<Project> findAll() {
        return projectDAO.findAll();
    }

    public void create(Project project) {
        projectDAO.persist(project.getKey(), project);
    }

    public Optional<Project> findByKey(String projectDescriptorName) {
        return projectDAO.findByKey(projectDescriptorName, projectDescriptorName);
    }

    public void update(Project project) {
        projectDAO.update(project.getKey(), project);
    }

    public void delete(Project project) {
        projectDAO.remove(project.getKey(), project);
    }

}
