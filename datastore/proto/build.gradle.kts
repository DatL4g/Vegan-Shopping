plugins {
    kotlin("jvm")
    alias(libs.plugins.protobuf)
}

val artifact = "dev.datlag.vegan.shopping.datastore.proto"
group = artifact

dependencies {
    api(libs.protobuf)
    api(libs.grpc)
}

protobuf {
    protoc {
        artifact = libs.protoc.get().toString()
    }
}