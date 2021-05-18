CREATE TRIGGER check_order_conflict_trigger
BEFORE INSERT ON t_order
FOR EACH ROW
EXECUTE PROCEDURE check_order_conflict();