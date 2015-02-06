# bundle the javascript
genrule(
    name = '_bundle-javascript',
    srcs = glob( [ 'src/main/javascript/**/*' ] ),
    cmd = 'browserify --debug src/main/javascript/home.js -o $OUT',
    out = 'home.js'
)

# move it into an appropriate sub directory and jar it
genrule(
    name = '_repackage-javascript',
    cmd = 'mkdir -p public/gen/js && cp $(location :_bundle-javascript) public/gen/js/ && jar cf $OUT public',
    out = 'bundled-javascript.jar',
    deps = [ ':_bundle-javascript' ]
)

# expose the jar
prebuilt_jar(
    name = 'bundled-javascript',
    binary_jar = ':_repackage-javascript'
)

compile_scss(
    name = 'css',
    resources_root = 'src/main/scss',
    input_file = 'main.scss',
    output_file = 'public/css/main.css',
    resources = glob( [ 'src/main/scss/**/*.scss' ] )
)

java_binary(
    name = 'srbb',
    main_class = 'com.github.mread.Server',
    deps = [
        ':core'
    ]
)

java_library(
    name = 'core',
    srcs = glob( [ 'src/main/java/**/*.java' ] ),
    resources_root = 'src/main/resources',
    resources = glob( [ 'src/main/resources/**/*' ] ),
    deps = [
        '//lib:gson',
        '//lib:jade4j',
        '//lib:jedis',
        '//lib:spark-core',
        ':bundled-javascript',
        ':css',
    ]
)

java_test(
    name = 'core-test',
    srcs = glob( [ 'src/test/java/**/*.java' ] ),
    deps = [
        ':core'
    ]
)

project_config(
    src_target = ':core',
    src_roots = [ 'src/main/java', 'src/main/resources' ],
    test_target = ':core-test',
    test_roots = [ 'src/test/java' ]
)


