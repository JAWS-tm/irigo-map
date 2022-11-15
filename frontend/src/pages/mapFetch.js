import { useEffect, useState } from 'react';
import axios from 'axios';

function useFetch(url) {
  const [datad, setDatas] = useState(null);

  useEffect(() => {
    axios.get(url).then((response) => {
      setDatas(response.data);
    });
  }, [url]);
  return datad;
}

export default useFetch;
