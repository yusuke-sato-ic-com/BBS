CREATE VIEW user_message AS
SELECT
name, message.id AS id, user_id, user.branch AS branch_id, user.department AS department_id, title, category, text, message.insert_date AS insert_date
FROM
user, message
WHERE
user.id = message.user_id;