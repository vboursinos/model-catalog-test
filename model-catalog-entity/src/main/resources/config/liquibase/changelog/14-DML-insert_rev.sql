create or replace function create_rev() returns integer
    language sql
as
$$
    insert into revinfo (rev, revtstmp)
    values (nextval('hibernate_sequence'), CURRENT_TIMESTAMP)
    returning rev
$$;