@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ContentType


def call(result, token, projectName) {
    println "咯咯咯咯"
    def title, text, picName
    if (result == 'SUCCESS') {
        title = "${projectName} 构建成功"
        text = "项目 [${projectName}] 构建成功"
        picName = "success.png"
    }
    else if (result == 'FAILURE') {
        title = "${projectName} 构建失败"
        text = "项目 [${projectName}] 构建失败"
        picName = "failure.png"
    }
    else {
        return
    }

    def payload = """
        {
            "msgtype": "link", 
            "link": {
                "text":"${text}", 
                "title": "${title}", 
                "picUrl": "http://or5sx3zbs.bkt.clouddn.com/jenkins/${picName}", 
                "messageUrl": "http://127.0.0.1:8080"
            }
        }
    """

    def webhook = "https://oapi.dingtalk.com/robot/send?access_token=${token}"

    def http = new HTTPBuilder(webhook)    

    println http

    println  "execute before"

    http.request(Method.POST, ContentType.JSON) {
        req ->
            headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"  
            headers.Accept = 'application/json'  
            body = payload

            println "req"

            response.success = { resp, json ->
                println resp.statusLine
                println json
            }

            // handler for any failure status code:
            response.failure = { resp ->
                println "Unexpected error: ${resp}"
            }
    }

    println  "execute after"
}

