namespace java game.thrift
namespace csharp Generate

struct RegisterInfo{
	1:string username,
	2:string password,
	3:string email
}

service UserStorage{
	i64 generateSessionId();

}