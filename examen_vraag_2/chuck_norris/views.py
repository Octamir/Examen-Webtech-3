from django.shortcuts import render
import redis
import urllib2
import json

r = redis.StrictRedis(host='localhost', port=6379, db=0)

def index(request):
    voornaam = request.GET.get('voornaam', '')
    achternaam = request.GET.get('achternaam', '')
    if voornaam == '' or achternaam == '':
        return index_without_params(request)
    else:
        return index_with_params(request, voornaam.lower(), achternaam.lower())

# Slechts een simpele form
def index_without_params(request):
    return render(request, 'chuck_norris/index_form.html', {})

# We passen de voor- en achternaam mee door omdat dit de code makkelijker maakt
def index_with_params(request, voornaam, achternaam):
    # We hebben enkel de joke nodig dus die halen we onmiddellijk uit het resultaat
    response_val = json.loads(urllib2.urlopen('http://api.icndb.com/jokes/random?firstName=Chuck&lastName=Norris').read())['value']
    joke = response_val['joke']
    id = response_val['id']
    key = voornaam + ':' + achternaam + ':' + str(id)

    # De jokes zullen worden opgeslagen als value, en hun key ziet eruit als {VOORNAAM}:{ACHTERNAAM}:{ID}
    if not r.exists(key):
        r.set(key, joke)

    return render(request, 'chuck_norris/index_joke.html', { 'full_name' : voornaam.capitalize() + ' ' + achternaam.capitalize(),
                                                             'joke' : joke })
