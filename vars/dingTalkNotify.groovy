@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7' )
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ContentType


def call(result, token, projectName) {
    println result
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
                "messageUrl": "https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.Rqyvqo&treeId=257&articleId=105735&docType=1"
            }
        }
    """
    def http = new HTTPBuilder("https://oapi.dingtalk.com/robot/send?access_token=${token}")

    http.request(Method.POST, ContentType.JSON) {
        req ->
            body = payload
            response.success = { resp, json ->
                println resp.statusLine
                println json
            }

            // handler for any failure status code:
            response.failure = { resp ->
                println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
            }
    }
}

