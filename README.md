# Постановка на миграционный учет по месту пребывания

- Запустить сервер в IDE (FinalprojectApplication.java)

## Пользователь
- Авторизация - http://localhost:8080/login
- Регистрация - http://localhost:8080/registration
- Мои заявки (МОЖНО ПОПАСТЬ ТОЛЬКО ПОСЛЕ Логина) - http://localhost:8080/user_doc
- Создать заявку (МОЖНО ПОПАСТЬ ТОЛЬКО ЧЕРЕЗ Мои заявки) - http://localhost:8080/createApplication


## Администратор
- Администратор заявки (МОЖНО ПОПАСТЬ ТОЛЬКО ПОСЛЕ Логина) - http://localhost:8080/admin_doc
- Посмотреть заявку - http://localhost:8080/openApplication
- Работа с заявкой заявку (подтвердить/отклонить/оставить комментарий) - http://localhost:8080/setApplicationStatus


### БД
- Открыть БД - http://localhost:8080/h2-console
- VISA_APPLICATION - Заявки
- USERS - Пользователи

### Статусы Заявок: 
- 0 - В работе
- 1 - Одобрено
- 2 - Отклонено

