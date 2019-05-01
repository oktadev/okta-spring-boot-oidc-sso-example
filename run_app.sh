#!/bin/bash

PORT=8080

usage() {
    echo "usage: ./run_app --ci <client id> --cs <client secret> --is <issuer> [--po <port>]"
}

[ $? -eq 0 ] || {
    echo "Incorrect options provided"
    exit 1
}

while test -n "$1"; do
    case "$1" in
    --ci)
        CLIENT_ID=$2
        shift 2
        ;;
    --cs)
        CLIENT_SECRET=$2
        shift 2
        ;;
    --is)
        ISSUER=$2
        shift 2
        ;;
    --po)
        PORT=$2
        shift 2
        ;;
    esac
done

[ -z "$CLIENT_ID" ] && usage && echo "--ci is required" && exit 1
[ -z "$CLIENT_SECRET" ] && usage && echo "--cs is required" && exit 1
[ -z "$ISSUER" ] && usage && echo "--is is required" && exit 1

mvn spring-boot:run \
    -Dokta.oauth2.clientId=$CLIENT_ID \
    -Dokta.oauth2.clientSecret=$CLIENT_SECRET \
    -Dokta.oauth2.issuer=$ISSUER \
    -Dserver.port=$PORT