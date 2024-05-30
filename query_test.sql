7. SELECT name, AVG(score) FROM USER_SCORES GROUP BY name;
8. SELECT uss.name, uss.emotion FROM (SELECT us.name, us.emotion, us.cnt, MAX(us.cnt) as max_cnt FROM (SELECT name, emotion, COUNT(emotion) as cnt 
FROM USER_SCORES GROUP BY name, emotion) us GROUP BY us.name, us.emotion, us.cnt) uss
WHERE uss.cnt = uss.max_cnt;
9. SELECT uss.name, uss.emotion, uss.avg_score FROM (SELECT us.name, us.emotion, us.cnt, us.avg_score, MAX(us.cnt) as max_cnt FROM (SELECT name, emotion, AVG(score) as avg_score, COUNT(emotion) as cnt 
FROM USER_SCORES GROUP BY name, emotion) us GROUP BY us.name, us.emotion, us.cnt, us.avg_score) uss
WHERE uss.cnt = uss.max_cnt;