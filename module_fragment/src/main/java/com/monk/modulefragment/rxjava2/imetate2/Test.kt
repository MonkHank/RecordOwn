package com.monk.modulefragment.rxjava2.imetate2

/**
 * @author monk
 * @date 2019-03-01
 */
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableCreator.ObservableEmitter<String>?) {
                emitter!!.onNext("test")
                emitter.onNext("tes1")
                emitter.onCompleted()
            }
        }).subscribe(object : Observer<String> {
            override fun onSubscribe(disposable: Disposable?) {
                println("onSubscribe")
            }

            override fun onNext(str: String) {
                println(str)
            }

            override fun onCompleted() {
                println("onCompleted")
            }

            override fun onError(throwable: Throwable?) {}
        })
        val observableOnSubscribe: ObservableOnSubscribe<String> = object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableCreator.ObservableEmitter<String>?) {
                emitter!!.onNext("1")
                emitter.onNext("2")
            }
        }
        val creator = ObservableCreator(observableOnSubscribe)
        creator.subscribeActual(object : Observer<String> {
            override fun onSubscribe(disposable: Disposable?) {}
            override fun onNext(o: String) {
                println(o)
            }

            override fun onCompleted() {}
            override fun onError(throwable: Throwable?) {}
        })
    }
}