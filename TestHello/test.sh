echo "Testing for 30s with 12 connections and 12 threads."
wrk -c12 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
wrk -c12 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
wrk -c12 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'

echo -e "\n"
echo "Testing for 30s with 100 connections and 12 threads."
wrk -c100 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
wrk -c100 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
wrk -c100 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'

echo -e "\n"
echo "Testing for 30s with 500 connections and 12 threads."
wrk -c500 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
wrk -c500 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
wrk -c500 -d30s -t12 http://localhost:8080/ | grep 'Latency\s*\w*.\w*\|Request\|Socket'
