
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

    def post = new URL(webhook).openConnection()
    post.setRequestMethod("POST")
    post.setDoOutput(true)
    post.setRequestProperty("Content-Type", "application/json")
    post.getOutputStream().write(payload.getBytes("UTF-8"))
    def postRC = post.getResponseCode()
    println(postRC)
    if(postRC.equals(200)) {
        println(post.getInputStream().getText())
    }
}

