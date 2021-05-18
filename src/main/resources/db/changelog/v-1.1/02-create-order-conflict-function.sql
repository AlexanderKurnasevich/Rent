CREATE FUNCTION check_order_conflict()
    RETURNS trigger AS $$
BEGIN
        IF (SELECT count(*) FROM t_order WHERE NEW.car_id = car_id AND
    (NEW.first_day BETWEEN first_day AND last_day OR NEW.last_day BETWEEN first_day AND last_day
        OR first_day BETWEEN NEW.first_day AND NEW.last_day OR last_day BETWEEN NEW.first_day AND NEW.last_day) AND
        order_status <> 'REFUSED') > 0 THEN
    RAISE EXCEPTION 'Order has conflict';
END IF;

RETURN NEW;
END;
    $$ LANGUAGE plpgsql