package server.database;

import commons.Activity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class ActivityLoadRunnerRepoMock implements ActivityRepository{

    private List<Activity> list;

    public ActivityLoadRunnerRepoMock() {
        this.list = new ArrayList<>();
    }

    @Override
    public List<Activity> findAll() {
        byte[] data = new byte[20];
        new Random().nextBytes(data);

        Activity ac1 = new Activity(data,"testtest",100);
        Activity ac2 = new Activity(data,"mestmest",200);
        Activity ac3 = new Activity(data,"restrest",300);
        Activity ac4 = new Activity(data,"festfest",400);
        Activity ac5 = new Activity(data,"zestzest",600);
        Activity ac6 = new Activity(data,"beszvest",700);
        Activity ac7 = new Activity(data,"ztzfsest",800);
        Activity ac8 = new Activity(data,"asdszest",900);

        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(ac1);
        activities.add(ac2);
        activities.add(ac3);
        activities.add(ac4);
        activities.add(ac5);
        activities.add(ac6);
        activities.add(ac7);
        activities.add(ac8);

        // returning an empty list, for the sake of testing activities to repo
        return list;
    }

    @Override
    public List<Activity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Activity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Activity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Activity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Activity> S save(S entity) {
        list.add(entity);
        return null;
    }

    @Override
    public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
        list.addAll((Collection<? extends Activity>) entities);
        return null;
    }

    @Override
    public Optional<Activity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Activity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Activity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Activity getOne(Long aLong) {
        return null;
    }

    @Override
    public Activity getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Activity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Activity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Activity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Activity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
