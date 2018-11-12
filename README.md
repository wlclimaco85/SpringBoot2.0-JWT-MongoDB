# SpringBoot2.0 JWT MongoDB

This application will setup the basic spring boot 2.0 server with mongodb jwt token authentication. 

Import it as a maven project then build it.

Run the below script in mongo db for the base user.

{
    "_id" : "101",
    "firstName" : "admin",
    "lastName" : "admin",
    "email" : "UlB2a0qmuv+GZ1UlcH5ZxA==",
    "contact" : "",
    "employeeId" : "user1",
    "userStatus" : "Active",
    "createTime" : ISODate("2018-11-12T09:04:11.873Z"),
    "roleInfo" : {
        "_id" : "ROLE_ADMIN",
        "roleName" : "Role Admin",
        "roleCode" : "ROLE_ADMIN"
    },
    "loginInfo" : {
        "password" : "24326124313024776862395046654c7a2e396c6a6e30783544453569756d39614272366555736f707136397975766246685542302e557173492f6647",
        "loginAttempts" : 0,
        "loginAccountStatus" : "Active",
        "statusReason" : "Status Reason",
        "pwdChangeReq" : "NO",
        "lastPwdDate" : "2017-11-02T06:32:42.503",
        "loginType" : "LoginType,
        "isFirstLogin" : false,
        "consentAccept" : true
    }
}

Then open the swagger UI in the browser

http://localhost:8443/api/swagger-ui.html

