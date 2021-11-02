package com.company.repository;
import com.company.model.Course;

public class CourseRepository extends InMemoryRepository<Course>{

    /**
     * @param entity entity must not be null
     * @return the older entity - if the entity is updated, otherwise returns the entity - (e.g. id does not exist).
     */
    @Override
    public Course update(Course entity) {
        for(Course c:repoList)
        {
            if(c.getCourseName() == entity.getCourseName())
                c.setCredits(entity.getCredits());
            return c;
        }
        return entity;
    }
}
