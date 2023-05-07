-- Test User
INSERT INTO `user` SET email='ybchar@test.com', nickname='hi', password='qwer1234', userRole='ADMIN'
    ON DUPLICATE KEY UPDATE email='ybchar@test.com', nickname='hi', password='qwer1234', userRole='ADMIN';
INSERT INTO `user` SET email='tester1@test.com', nickname='test1', password='qwer1234', userRole='USER'
    ON DUPLICATE KEY UPDATE email='tester1@test.com', nickname='test1', password='qwer1234', userRole='USER';
INSERT INTO `user` SET email='tester2@test.com', nickname='test2', password='qwer1234', userRole='USER'
    ON DUPLICATE KEY UPDATE email='tester2@test.com', nickname='test2', password='qwer1234', userRole='USER';
INSERT INTO `user` SET email='tester3@test.com', nickname='test3', password='qwer1234', userRole='USER'
    ON DUPLICATE KEY UPDATE email='tester3@test.com', nickname='test3', password='qwer1234', userRole='USER';
INSERT INTO `user` SET email='tester4@test.com', nickname='test4', password='qwer1234', userRole='USER'
    ON DUPLICATE KEY UPDATE email='tester4@test.com', nickname='test4', password='qwer1234', userRole='USER';
