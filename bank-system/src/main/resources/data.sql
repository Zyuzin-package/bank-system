INSERT INTO CLIENT values ('20E60E0175D4F44CD6F7947883DDD4D0', '1', '2', '3', '4', '5');
INSERT INTO CLIENT values ('20E60E0175D4F44CD6F7947883DDD4D1', '11', '22', '33', '44', '55');
INSERT INTO CLIENT values ('20E60E0175D4F44CD6F7947883DDD4D2', '111', '222', '333', '444', '555');

INSERT INTO CREDIT VALUES ('20E60E0175D4F44CD6F7947883DDD5A1', 50000, 5);
INSERT INTO CREDIT VALUES ('20E60E0175D4F44CD6F7947883DDD5A2', 80000, 7);

INSERT INTO payment_event (ID,CREDIT_SUM,INTEREST_SUM,PAYMENT_SUM,LOCAL_DATE,CREDIT_OFFER_ID) VALUES '20E60E0175D4F44CD6F7947883DDD5B1', 50, 50, 100,'2022-01-22',null;

INSERT INTO credit_offer VALUES ('20E60E0175D4F44CD6F7947883DDD5C1', '20E60E0175D4F44CD6F7947883DDD4D0', '20E60E0175D4F44CD6F7947883DDD5A1');

INSERT INTO credit_offer_payment_event_list VALUES ('20E60E0175D4F44CD6F7947883DDD5C1', '20E60E0175D4F44CD6F7947883DDD5B1');

UPDATE PAYMENT_EVENT  SET CREDIT_OFFER_ID = '20E60E0175D4F44CD6F7947883DDD5C1' WHERE ID='20E60E0175D4F44CD6F7947883DDD5B1';