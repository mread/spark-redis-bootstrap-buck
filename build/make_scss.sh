#!/bin/bash
set -euo pipefail
IFS=$'\n\t'
TMP=`mktemp -d`

ASSETS_DIR=${TMP}/assets/unpack
GENERATED_ASSETS_DIR=${TMP}/main/generated-assets

mkdir -p ${ASSETS_DIR}

while getopts "r:s:o:l:" OPT ; do

  case $OPT in
    r)
      JRUBY_PATH=$OPTARG
      ;;

    s)
      SASS_PATH=$OPTARG
      ;;

    o)
      OUT_FILE=$OPTARG
      ;;

    l)
      LIB=$OPTARG
      ( cd ${ASSETS_DIR} ; jar xf ${LIB} )
      ;;

    ?)
      exit 1
      ;;
  esac

done

shift $((OPTIND -1))
SRC_DIR=$1


SCSS_INFILE=${SRC_DIR}/$2
CSS_OUTFILE=${GENERATED_ASSETS_DIR}/$3
DEBUG=false

mkdir -p `dirname $CSS_OUTFILE`

mkdir -p ${TMP}/scss
( cd ${TMP}/scss ; unzip ${SASS_PATH} )

echo "
require '${TMP}/scss/gems/sass-3.4.4/lib/sass'
require '${TMP}/scss/gems/sass-3.4.4/lib/sass/exec'

style = :compressed
if ('${DEBUG}' == 'true')
  style = :expanded
end

puts('Compiling ${SCSS_INFILE} to ${CSS_OUTFILE} using style ' + style.to_s)
Sass::compile_file('${SCSS_INFILE}', '${CSS_OUTFILE}', {:syntax => :scss, :full_exception => true, :style => style, :load_paths => ['.', '${ASSETS_DIR}']});
" > ${TMP}/scss/script_file

java -jar ${JRUBY_PATH} ${TMP}/scss/script_file



## Build war

jar cf ${OUT_FILE} -C ${GENERATED_ASSETS_DIR} .

## Clean-up

rm -r ${TMP}
